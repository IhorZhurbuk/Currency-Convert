import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CurrencyConvert currencyConvert = new CurrencyConvert(ExchangeClient.currency());
        currencyConvert.setVisible(true);

    }
}
