package repository;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.Address;

import java.util.List;

public class AddressRepository {
    private final DatabaseReaderService<Address> readerService;
    private final DatabaseWriterService<Address> writerService;

    public AddressRepository(DatabaseReaderService<Address> readerService, DatabaseWriterService<Address> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Address getAddressById(Long id) {
        return readerService.read(Address.class, id);
    }

    public List<Address> getAllAddresses() {
        return readerService.readAll(Address.class);
    }

    public void add(Address address) {
        writerService.write(address);
    }

    public void update(Address address) {
        writerService.update(address);
    }

    public void delete(Long id) {
        Address address = getAddressById(id);
        if (address != null) {
            writerService.delete(address);
        }
    }
}
