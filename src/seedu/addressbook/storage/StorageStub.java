package seedu.addressbook.storage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.storage.jaxb.AdaptedAddressBook;

public class StorageStub extends Storage {
    private final JAXBContext jaxbContext;

    public final Path path;
    public StorageStub(String filePath) {
        path = Paths.get(filePath);
        try {
            jaxbContext = JAXBContext.newInstance(AdaptedAddressBook.class);
        } catch (JAXBException jaxbe) {
            throw new RuntimeException("jaxb initialisation error");
        }

    }
    
    public AddressBook load() throws StorageOperationException{
        try (final Reader fileReader =
                new BufferedReader(new FileReader(path.toFile()))) {

       final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
       final AdaptedAddressBook loaded = (AdaptedAddressBook) unmarshaller.unmarshal(fileReader);
       // manual check for missing elements
       try {
            if (loaded.isAnyRequiredFieldMissing()) {
               throw new StorageOperationException("File data missing some elements");
            }
       } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }
       return loaded.toModelType();

   /* Note: Here, we are using an exception to create the file if it is missing. However, we should minimize
    * using exceptions to facilitate normal paths of execution. If we consider the missing file as a 'normal'
    * situation (i.e. not truly exceptional) we should not use an exception to handle it.
    */

   // create empty file if not found
   } catch (FileNotFoundException fnfe) {
       final AddressBook empty = new AddressBook();
       save(empty);
       return empty;

   // other errors
   } catch (IOException ioe) {
       throw new StorageOperationException("Error writing to file: " + path);
   } catch (JAXBException jaxbe) {
       throw new StorageOperationException("Error parsing file data format");
   } catch (IllegalValueException ive) {
       throw new StorageOperationException("File contains illegal data values; data type constraints not met");
   }

    }
    @Override
    public void save(AddressBook addressBook){
        //do nothing
    }

    @Override
    public String getPath() {
        return path.toString();
    }


}
