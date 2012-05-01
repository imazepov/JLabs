/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.misc;

/**
 *
 * @author ivan
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ReferenceField {
}
