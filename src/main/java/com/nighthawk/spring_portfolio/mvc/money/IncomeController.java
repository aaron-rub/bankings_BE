package com.nighthawk.spring_portfolio.mvc.money;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

@Controller
public class IncomeController {

    @PostMapping("/submitIncome")
    public String submitIncome(@RequestParam(name="income", required=true) String income) {
        // Create a JSONObject
        JSONObject obj = new JSONObject();

        // Put the income into the JSONObject
        obj.put("DailyIncome", income);

        try (FileWriter file = new FileWriter("/path/to/your/directory/daily.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
}
