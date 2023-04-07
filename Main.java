import domain.Address;
import repository.AddressRepository;
import service.AddressService;

public class Main {
    public static void main(String[] args) {
        AddressRepository addressRepository = new AddressRepository();
        AddressService addressService = new AddressService(addressRepository);

        addressService.create(1, "Bulevardul Eroilor", "Bucuresti", "Bucuresti", "093453", 10);

        Address retrievedAddress = addressService.getAddressById(1);
        System.out.println("Adresa:");
        System.out.println(retrievedAddress.toString());
    }
}
