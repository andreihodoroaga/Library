package service;

import domain.Address;
import repository.AddressRepository;

import java.util.List;

public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void createAddress(int id, String street, String city, String state, String postalCode, int number) {
        Address address = new Address(id, street, city, state, postalCode, number);
        addressRepository.addAddress(address);
    }

    public Address getAddressById(int id) {
        return addressRepository.getAddressById(id);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }

    public void updateAddress(Address address) {
        addressRepository.updateAddress(address);
    }

    public void deleteAddress(int addressId) {
        addressRepository.deleteAddress(addressId);
    }

    public void addAddress(Address address) {
        addressRepository.addAddress((address));
    }
}
