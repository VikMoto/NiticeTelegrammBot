import com.javacore10.featur.currency.CurrencyService;
import com.javacore10.featur.currency.PrivatBankCurrencyService;
import com.javacore10.featur.currency.dto.Currency;
import com.javacore10.featur.telegram.TelegramBotService;
import com.javacore10.featur.ui.PrettyPrintCurrencyServise;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.IOException;

public class TelegramBotApp {
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();


//        CurrencyService currencyService = new PrivatBankCurrencyService();
//        Currency currency = Currency.USD;
//        double rate = currencyService.getRate(currency);
//        String convert = new PrettyPrintCurrencyServise().convert(rate, currency);
//
//        System.out.println(convert);
    }
}
