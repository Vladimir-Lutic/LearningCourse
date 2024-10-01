package githubapi.testing;

import githubapi.testing.dto.ValCurs;
import githubapi.testing.dto.Valute;

import java.util.Comparator;

public class ValuteUtility {
    public static void validateNominal(ValCurs valCurs){

        for (Valute valute : valCurs.getValutes()){
            if (valute.getNominal() != 1){
                System.out.println(valute.getName() + " " + valute.getNominal() + " " + "ERROR");
            }
        }
    }

    public static void findRate (ValCurs valCurs, String valuteCode){
        for (Valute valute : valCurs.getValutes()){
            if (valute.getCharCode().equals(valuteCode)){
                System.out.println("Exchange rate for " + valuteCode + " = " + valute.getValue());
            }
        }
    }

    public static void getAvg (ValCurs valCurs){
        double i = 0;
        int count = 0;
        for (Valute valute : valCurs.getValutes()){
            i += valute.getValue();
            count++;
        }
        System.out.println("Average rate for all valutes: " + i/count);
    }

    public static void sortByValue (ValCurs valCurs){
        /*
        double temp = 19.4615;
        int countIndex = 0;
        for (int i = 0; i<valCurs.getValutes().size(); i++){
            System.out.println(valCurs.getValutes().get(i).getValue());
        }*/

        /*for (Valute valute : valCurs.getValutes()){
            System.out.println(valCurs.getValutes().get(countIndex).getValue());
            countIndex++;
        }*/

        valCurs.getValutes().sort(Comparator.comparing(Valute::getValue));

        for (Valute valute : valCurs.getValutes()){
            System.out.println(valute.getName() + " " + valute.getValue());
        }
    }

    public static void convert (ValCurs valCurs, String valuteCode){
        double exchangeRate = 0;
        double baseValue = 0;
        double targetValue = 0;
        int startConversion = 0;

        //Find the value of the base currency
        for (Valute valute : valCurs.getValutes()){
            if (valute.getCharCode().equals(valuteCode)){
                baseValue = valute.getValue()/valute.getNominal();
                startConversion = 1;
            }
        }

        //Find the value of the target currency and convert
        if (startConversion == 1){
            System.out.println("Starting conversion:");
            for (Valute valute : valCurs.getValutes()){
                exchangeRate = baseValue/(valute.getValue()/valute.getNominal());
                targetValue = 1 * exchangeRate;
                System.out.println("1 " + valuteCode + " = " + targetValue + " " + valute.getCharCode());
            }
        }
        else{
            System.out.println("Valute code not found!");
        }
    }

}
