package com.f.security.model;

import com.f.metadata.model.Metadata;
import com.f.security.model.object.DataObject;

/**
 * Defines the importer director role of the builder that imports a User
 */
public interface UserImporter {

   /**
    * Set up the security guard
    * @param guard guard of the user object
    */
   public void setGuard( DataObject  guard);


   /**
    * Set up the basic user information
    * @param userId   User code
    * @param firstName First name
    * @param lastName  Last name
    * @param email     Email address
    */
   public void setBasic( String userId, String  firstName, String lastName, String  email);


   /**
    * Add a required role
    * @param requiredRole required role
    */
   public void addRequiredRole( Role requiredRole);


   /**
    * Add an enabling role
    * @param enablingRole enabling role
    */
   public void addEnablingRole( Role enablingRole);


   /**
    * Set up user metadata
    * @param metadata user's metadata. Can be null indicating there is no metadata
    */
   public void setMetadata( Metadata metadata);
}
