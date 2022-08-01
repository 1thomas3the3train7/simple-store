package com.example.MYSTORE.Controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResponse {
    private String ans1;
    private String ans2;
    private String ans3;

    public JsonResponse(String ans1, String ans2, String ans3) {
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
    }

    public JsonResponse(String ans1, String ans2) {
        this.ans1 = ans1;
        this.ans2 = ans2;
    }

    public JsonResponse(String ans1) {
        this.ans1 = ans1;
    }
}
