package pl.edu.pjwstk.jaz.average;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AverageController {

    @GetMapping("average")
    public String getAverage(@RequestParam(value = "numbers",required = false) String numbers) {

        if(numbers == null) return "Please put parameters";
        else if(numbers.isEmpty()) return "Please put parameters";
        else {

            String[] numbersStringArray = numbers.split(",");
            double sum = 0;

            for (String number : numbersStringArray) {
                try {
                    sum += Double.parseDouble(number);
                } catch (IllegalArgumentException e) {
                    return "\'" + number + "\'" + " is not a number!";
                }
            }

            Double result = sum / numbersStringArray.length;

            double roundOff = Math.round(result * 100.0) / 100.0;

            Integer c = (int) roundOff;

            if (roundOff - c > 0.0)
                return "Average equals: " + roundOff;
            else
                return "Average equals: " + (int) roundOff;
        }
    }



}
