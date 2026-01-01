<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment - XYZ Gym</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>
<body style="background:#121212; color:#e0e0e0; font-family:'Poppins',sans-serif; padding:40px;">

<div class="container" style="max-width:600px;">
    <h3>Pay for Plan: ${plan.planName}</h3>
    <p>Duration: ${plan.durationMonths} months</p>
    <p>Amount: ₹ ${plan.price}</p>

    <button id="rzp-button" class="btn btn-primary mt-3 w-100">Pay Now</button>
</div>

<script>
    var options = {
        "key": "rzp_test_RyWxUjYDSPBuhj", // 🔥 Replace with your Razorpay test key
        "amount": ${plan.price * 100}, // in paise
        "currency": "INR",
        "name": "XYZ Gym",
        "description": "${plan.planName} Plan",
        "handler": function (response){
            // Payment success → call server to save payment
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/member/paymentSuccess", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if(xhr.readyState === 4 && xhr.status === 200){
                    window.location.href = "/member/dashboard";
                }
            };
            xhr.send("razorpayPaymentId=" + response.razorpay_payment_id);
        },
        "theme": { "color": "#ff6f00" }
    };
    var rzp1 = new Razorpay(options);
    document.getElementById('rzp-button').onclick = function(e){
        rzp1.open();
        e.preventDefault();
    }
</script>
</body>
</html>
