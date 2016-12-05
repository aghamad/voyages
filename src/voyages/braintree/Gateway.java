// Fichier Gateway.java
// Auteur : Gilles Benichou
// Date de création : 5 déc. 2016

package voyages.braintree;

import java.math.BigDecimal;
import com.braintreegateway.Address;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;

/**
 * TODO Auto-generated class javadoc
 *
 * @author Vincent Laferrière
 */
public class Gateway {
    private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
        "rzvnmsg4ty5zkmsh",
        "ktn3vv36jgs96bk5",
        "ea6317cc3127e9049245404271408b6f");

    public static String GenerateToken() {
        return gateway.clientToken().generate();
    }

    public static Result<Transaction> DoTransaction(String nonce,
        Customer customer,
        Address address,
        BigDecimal amount) {
        return gateway.transaction()
            .sale(new TransactionRequest().amount(amount).paymentMethodNonce(nonce).customer().id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).company(customer.getCompany()).phone(customer.getPhone()).fax(customer.getFax())
                .website(customer.getWebsite()).email(customer.getEmail()).done().billingAddress().firstName(address.getFirstName())
                .lastName(address.getLastName()).company(address.getCompany()).streetAddress(address.getStreetAddress())
                .extendedAddress(address.getExtendedAddress()).locality(address.getLocality()).region(address.getRegion()).postalCode(address.getPostalCode())
                .countryCodeAlpha2(address.getCountryCodeAlpha2()).countryCodeAlpha3(address.getCountryCodeAlpha3()).done().options()
                .submitForSettlement(Boolean.TRUE).done());
    }
}
