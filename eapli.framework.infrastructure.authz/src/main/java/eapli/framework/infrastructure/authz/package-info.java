/**
 * 
 *
 Authentication and Authorizations 

A system user is (typically) a person who has been granted access to the system. The user logins using her username and password; if the username and password match the stored credentials, a user session is established for that user: the user is authenticated. Each user can be assigned to one or more roles which an application can use for separating access to different features, enabling role-based authorizations.
Sometimes the system administrator can deactivate a user disabling her ability to login to the system. A user can afterward be activated again.
Each user can be unassigned from a role but their assignment is kept for historic purposes. Checking the roles of a user for authorization purposes only takes into account the currently assigned roles, not the history. A role is simply a non empty label with meaning to the client application (and possibly to a human user). An assignment has a starting date and, if unassigned, an end date.


Use cases

Create user account
Login
Change password
Reset the password of a user
Activate/deactivate a user
Check if a user has any of certain roles
Get the currently authenticated user
Check if the authenticated user has any of certain roles


Additional requirements

An email, first name, last name must be kept for each system user
Usernames must be unique in the system
A password must be at least 6 characters, must contain at least one digit and at least one capital letter
Names must not be empty
Email addresses must be syntactically valid

 
 *
 * @author Paulo Gandra Sousa
 *
 */
package eapli.framework.infrastructure.authz;