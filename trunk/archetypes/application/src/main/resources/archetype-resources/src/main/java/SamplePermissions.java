#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

/*
 * This class is scanned by the SimpleConstantsPermissionAware bean as configured in module-context.xml. Every
 * encountered string constant is made known to the permission system and can be consequently assigned to roles in the
 * user management part of the application. The permissions are internationalizable by adding entries to your message
 * source. The key used to look up translations is constructed as umt.permission.<permission string>.
 */
public abstract class SamplePermissions {

    // An example permission for our ItemController
    public static final String ITEMS_DELETE = "ITEMS_DELETE";
}
