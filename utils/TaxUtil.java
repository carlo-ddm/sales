package utils;
import constants.ExemptProduct;
import java.util.stream.Stream;

public class TaxUtil {

    public static boolean isExemptFromTax(String productName) {
        return Stream.of(ExemptProduct.values())
                .map(ExemptProduct::getProductName)
                .anyMatch(productName::contains);
    }
}
