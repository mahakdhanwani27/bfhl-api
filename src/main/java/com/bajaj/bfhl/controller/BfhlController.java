package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.model.RequestData;
import com.bajaj.bfhl.model.ResponseData;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BfhlController {

    @PostMapping("/bfhl")
    public ResponseData processData(@RequestBody RequestData requestData) {
        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();

        for (String item : requestData.getData()) {
            if (item.matches("\\d+")) {
                numbers.add(item);
            } else {
                alphabets.add(item);
            }
        }

        ResponseData response = new ResponseData();
        response.setNumbers(numbers);
        response.setAlphabets(alphabets);

        return response;
    }
}