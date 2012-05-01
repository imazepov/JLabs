/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.adapters;

import carrental.data.DbConnection;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import carrental.misc.IdField;
import carrental.misc.ReferenceField;
import carrental.model.Model;

/**
 *
 * @author ivan
 */
public abstract class Adapter<T extends Model> {
    protected DbConnection connection;
    protected Class modelClass;
    
    protected Adapter(DbConnection conn, Class c) throws AdapterException {
        if(conn == null || !conn.isValid() || c == null)
            throw new InvalidParameterException("conn");
        
        try {
            T t = (T)c.newInstance();
        } catch(ClassCastException ex) {
            throw new InvalidParameterException("T != c");
        } catch(InstantiationException ex) {
            throw new AdapterException("Could not instantiate model", ex);
        } catch(IllegalAccessException ex) {
            throw new AdapterException("Access error for model");
        }
        
        connection = conn;
        modelClass = c;
    }
    
    public List<T> getObjects() throws SQLException, AdapterException {
        return getObjects(new HashMap<String, Object>());
    }
    
    public List<T> getObjects(Map<String, Object> params) 
            throws SQLException, AdapterException {
        if(params == null)
            throw new InvalidParameterException("params");
               
        ArrayList paramList = new ArrayList();
        
        String tableName = modelClass.getSimpleName();
        String query = prepareQueryString(tableName, params, paramList);
        
        ArrayList<T> result = new ArrayList<T>();
        
        ResultSet rs = connection.executeQuery(query, paramList);
        ResultSetMetaData metaData = rs.getMetaData();
        while(rs.next()) {
            result.add(getObjectFromResultSet(rs, metaData));
        }
        
        return result;
    }
    
    public void saveObject(T obj) throws AdapterException {
        if(obj.getId() == 0) {
            addObject(obj);
            return;
        }
        
        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList fieldValues = new ArrayList();
        
        getFieldNamesAndValues(obj, fieldNames, fieldValues);
        
        StringBuilder queryBuilder = new StringBuilder("UPDATE `")
                .append(modelClass.getSimpleName())
                .append("` SET ");
        for(int i=0; i<fieldNames.size(); i++) {
            if(i != 0) {
                queryBuilder.append(",");
            }
            
            queryBuilder.append("`")
                    .append(fieldNames.get(i))
                    .append("` = ?");
        }
        
        queryBuilder.append(" WHERE ID = ").append(obj.getId());
        
        try {
            connection.executeQuery(queryBuilder.toString(), fieldValues);
        } catch (SQLException ex) {
            throw new AdapterException("Failed to execute update query", ex);
        }
    }
    
    public int addObject(T newObj) throws AdapterException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(modelClass.getSimpleName());
        
        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList fieldValues = new ArrayList();
        
        getFieldNamesAndValues(newObj, fieldNames, fieldValues);
        
        queryBuilder.append(" (");
        for(int i=0; i<fieldNames.size()-1; i++) {
            queryBuilder.append("`").append(fieldNames.get(i)).append("`");
            queryBuilder.append(",");
        }
        queryBuilder.append("`").append(fieldNames.get(fieldNames.size()-1)).append("`)");
        
        queryBuilder.append(" VALUES ");
        
        queryBuilder.append("(");
        for(int i=0; i<fieldValues.size()-1; i++) {
            // queryBuilder.append(fieldValues.get(i));
            queryBuilder.append("?,");            
        }
        queryBuilder.append("?)");
        
        try {
            connection.beginTransaction();
        } catch(SQLException ex) {
            throw new AdapterException("Error starting transaction", ex);
        }
        
        int insertedId = 0;
        try {            
            connection.executeQuery(queryBuilder.toString(), fieldValues);
            ResultSet rs = connection.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()) {
                insertedId = rs.getInt(1);
            } else {
                throw new AdapterException("Error getting insert_id");
            }
            connection.commitTransaction();
        } catch(SQLException ex) {
            try {
            connection.rollbackTransaction();
            } catch(Exception x) {
            }
            throw new AdapterException("Error executing query", ex);
        }
        
        newObj.setId(insertedId);
        
        return insertedId;
    }

    private void getFieldNamesAndValues(T newObj, ArrayList<String> fieldNames, ArrayList fieldValues) throws SecurityException, AdapterException {
        Field[] fields = modelClass.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if(!isAnnotationPresentInField(field, ReferenceField.class)
                    && !isAnnotationPresentInField(field, IdField.class)) {
                final String fieldName = field.getName();
                fieldNames.add(fieldName);
                final Object fieldValue;
                try {
                    fieldValue = field.get(newObj);
                } catch (IllegalArgumentException ex) {
                    throw new AdapterException("Cannot get field '" + fieldName + "'", ex);
                } catch (IllegalAccessException ex) {
                    throw new AdapterException("Cannot access field '" + fieldName + "'", ex);
                }

                fieldValues.add(fieldValue);
            }
        }
    }
    
    public void removeObject(T obj) throws AdapterException {
        removeObject(obj.getId());
    }
    
    public void removeObject(int id) throws AdapterException {
        try {
            ArrayList params = new ArrayList();
            params.add(id);
            connection.executeQuery("DELETE FROM `" + modelClass.getSimpleName() + "` WHERE ID = ?",
                    params);
        } catch(SQLException ex) {
            throw new AdapterException("Failed to execute remove query", ex);
        }
    }

    private String prepareQueryString(String tableName, Map<String, Object> params, ArrayList paramList) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + tableName);
        
        Field[] fields = modelClass.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            boolean referenceAnnotationPresent = isAnnotationPresentInField(field, ReferenceField.class);
            
            if(referenceAnnotationPresent) {
                final String fieldClassName = field.getType().getSimpleName();
                queryBuilder.append(String.format(" JOIN `%s` ON `%s`.ID = `%s`.%sID", 
                        fieldClassName, fieldClassName, tableName, fieldClassName));
            }
        }
                
        boolean firstInserted = false;
        for(String key : params.keySet()) {
            if(!firstInserted) {
                queryBuilder.append(" WHERE ");
            } else {
                queryBuilder.append(" AND ");
            }
            
            queryBuilder.append(key).append(" = ?");
            paramList.add(params.get(key));
            firstInserted = true;
        }
        String query = queryBuilder.toString();
        return query;
    }

    private boolean isAnnotationPresentInField(Field field, Class annotationClass) {
        boolean referenceAnnotationPresent = false;
        Annotation[] anns = field.getDeclaredAnnotations();
        for(Annotation ann : anns) {
            if(ann.annotationType().equals(annotationClass)) {
                referenceAnnotationPresent = true;
                break;
            }
        }
        return referenceAnnotationPresent;
    }

    protected T getObjectFromResultSet(ResultSet rs, ResultSetMetaData metaData) 
            throws SQLException, AdapterException {
        T result;    
        try {
            result = (T)modelClass.newInstance();
        } catch (InstantiationException ex) {
            throw new AdapterException("Cannot instantiate model", ex);
        } catch (IllegalAccessException ex) {
            throw new AdapterException("Cannot instantiate model", ex);
        }
               
        int fieldCount = metaData.getColumnCount();
        for(int i=1; i<=fieldCount; i++) {
            final String tableName = metaData.getTableName(i);
            final String className = modelClass.getSimpleName();
            if(tableName.equalsIgnoreCase(className)) {
                Field field;
                final String fieldName = metaData.getColumnName(i);                
                try {
                    field = findFieldIgnoreCase(fieldName, modelClass);
                    field.setAccessible(true);
                } catch (NoSuchFieldException ex) {
                    continue;
                } catch (SecurityException ex) {
                    throw new AdapterException("Cannot access field '" + fieldName + "' in the model", ex);
                }

                Object value;
                value = rs.getObject(i);

                try {
                    field.set(result, value);
                } catch (IllegalArgumentException ex) {
                    throw new AdapterException("Cannot set field '" + fieldName + "'", ex);
                } catch (IllegalAccessException ex) {
                    throw new AdapterException("Cannot access field '" + fieldName + "'", ex);
                }                
            }
        }
                
        return result;
    }

    private Field findFieldIgnoreCase(String fieldName, Class theClass) throws NoSuchFieldException {
        ArrayList<Field> fields = new ArrayList<Field>();

        fields.addAll(Arrays.asList(theClass.getDeclaredFields()));
        fields.addAll(Arrays.asList(theClass.getSuperclass().getDeclaredFields()));
        
        for(Field field : fields) {
            if(field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        
        throw new NoSuchFieldException();
    }
}
