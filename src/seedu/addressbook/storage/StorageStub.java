package seedu.addressbook.storage;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.storage.Storage.InvalidStorageFilePathException;
import seedu.addressbook.storage.Storage.StorageOperationException;

public class StorageStub extends Storage {

    public StorageStub(String filePath) throws InvalidStorageFilePathException {
        super(filePath);
    }
    
    @Override
    public void save(AddressBook addressBook) throws StorageOperationException {
        //do nothing
    }

}
