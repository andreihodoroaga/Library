package service;

import domain.Address;
import repository.AddressRepository;

import java.util.List;

public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void create( String street, String city, String state, String postalCode, int number) {
        Address address = new Address(street, city, state, postalCode, number);
        addressRepository.add(address);
    }

    public Address getAddressById(Long id) {
        return addressRepository.getAddressById(id);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }

    public void update(Address address) {
        addressRepository.update(address);
    }

    public void delete(Long addressId) {
        addressRepository.delete(addressId);
    }

    public void add(Address address) {
        addressRepository.add((address));
    }
}
