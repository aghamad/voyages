// Fichier Gateway.java
// Auteur : Gilles Benichou
// Date de création : 5 déc. 2016

package voyages.braintree;

import java.math.BigDecimal;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.PayPalAccount;
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
    
    public static PayPalAccount GetPaypalAccount(String token) {
        return gateway.paypalAccount().find(token);
    }
    public static Transaction GetPayment(String token) {
        return gateway.transaction().find(token);
    }

    public static Result<Transaction> DoTransaction(String nonce,
        BigDecimal amount) {
        TransactionRequest request = new TransactionRequest().amount(amount).paymentMethodNonce(nonce).orderId("Mapped to PayPal Invoice Number").options()
            .paypal().customField("PayPal custom field").description("Description for PayPal email receipt").done().storeInVaultOnSuccess(Boolean.TRUE).done();

        return gateway.transaction().sale(request);
    }
}
