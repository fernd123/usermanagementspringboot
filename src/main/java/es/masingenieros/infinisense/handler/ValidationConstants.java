package es.masingenieros.infinisense.handler;

/**
 * @author Fernando Rodriguez
 *
 */
public class ValidationConstants {
	public static final String VALID_PHONE_REGEX = "((([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4}))?";

	public static final String MSG_VALUE_REQURED = "required";
	public static final String MSG_VALUE_MUST_BE_UNIQUE = "must.be.unique";
	public static final String MSG_LANG_MUST_BE_UNIQUE = "entity.errors.translation.langMustBeUnique";
	public static final String MSG_VALUE_INVALID = "invalid";
	public static final String MSG_VALUE_INVALID_FORMAT = "invalid.format";

	public static final String MSG_VALIDATION_ERRORS = "validation.errors";

}
