import domain.Address;
import repository.AddressRepository;
import service.AddressService;

public class Main {
    public static void main(String[] args) {
        AddressRepository addressRepository = new AddressRepository();
        AddressService addressService = new AddressService(addressRepository);

        Address address = new Address(1, "123 Main St", "Apt 1", "New York", "NY", 12345);
        addressService.addAddress(address);

        Address retrievedAddress = addressService.getAddressById(address.getId());
        System.out.println("Retrieved address details:");
        System.out.println(retrievedAddress.toString());
    }
}
