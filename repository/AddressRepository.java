package repository;

import domain.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    private List<Address> addresses;

    public AddressRepository() {
        addresses = new ArrayList<>();
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public Address getAddressById(int id) {
        for (Address address : addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null;
    }

    public void updateAddress(Address address) {
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getId() == address.getId()) {
                addresses.set(i, address);
                break;
            }
        }
    }

    public void deleteAddress(int id) {
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getId() == id) {
                addresses.remove(i);
                break;
            }
        }
    }

    public List<Address> getAllAddresses() {
        return addresses;
    }
}
