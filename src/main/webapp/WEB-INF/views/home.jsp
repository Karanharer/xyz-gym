<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>XYZ Gym - Home</title>

    <!-- CSS -->
    <link rel="stylesheet" href="../css/navbar.css">
    <link rel="stylesheet" href="../css/home.css">
    <link rel="stylesheet" href="../css/footer.css">

    <!-- Icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<div id="loginModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeLoginModal()">&times;</span>

        <h2>Login to XYZ Gym</h2>

        <form action="/login" method="post">
            <select name="role" required>
                <option value="">Select Role</option>
                <option value="admin">Admin</option>
                <option value="member">Member</option>
                <option value="trainer">Trainer</option>
            </select>

            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>

            <button type="submit" class="btn primary">Login</button>
        </form>

        <!-- Show error if login fails -->
        <c:if test="${not empty error}">
            <script>
                window.onload = function () {
                    openLoginModal();
                };
            </script>
        </c:if>

        <!-- Sign Up link -->
        <p>Don't have an account? 
            <a href="javascript:void(0)" onclick="openRegisterPopup()">Sign Up</a>
        </p>
    </div>
</div>

<!-- Registration Popup -->
<div id="registerPopup" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeRegisterPopup()">&times;</span>
        <h2>Register at XYZ Gym</h2>
        <form action="/member/register" method="post">
            <!-- Role Dropdown -->
            <select name="role" required>
                <option value="">Select Role</option>
                <option value="member">Member</option>
                <option value="trainer">Trainer</option>
                <!-- Admin usually manually add करतात, म्हणून optional -->
            </select>
            <input type="text" name="name" placeholder="Name" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="number" name="phone" placeholder="phone" required>
            
            <select name="gender" required>
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
            </select>

            <input type="text" name="addressline1" placeholder="Address Line" required>

            <button type="submit" class="btn primary">Register</button>
        </form>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <c:if test="${not empty success}">
            <p class="success">${success}</p>
        </c:if>
    </div>
</div>

<!-- ============ REFUND POPUP ============ -->
<div id="refundPopup" class="modal">
    <div class="modal-content large">
        <span class="close" onclick="closeRefundPopup()">&times;</span>

        <h2>Cancellation & Refund Policy</h2>

        <p>
            XYZ Gym believes in fair usage of services. Please read the refund
            policy carefully.
        </p>

        <ul class="policy-list">
            <li>Membership once activated cannot be cancelled.</li>
            <li>No refunds for partially used memberships.</li>
            <li>Refund requests must be raised before plan activation.</li>
            <li>Approved refunds will be processed within 7–10 working days.</li>
            <li>Transaction charges are non-refundable.</li>
        </ul>

        <button class="btn primary" onclick="closeRefundPopup()">Close</button>
    </div>
</div>


<!-- ============ TERMS POPUP ============ -->
<div id="termsPopup" class="modal">
    <div class="modal-content large">
        <span class="close" onclick="closeTermsPopup()">&times;</span>

        <h2>Terms & Conditions</h2>

        <p>
            Welcome to XYZ Gym. By accessing or using our services, you agree
            to comply with the following terms and conditions.
        </p>

        <ul class="policy-list">
            <li>Membership fees are non-transferable.</li>
            <li>Members must follow gym rules and safety guidelines.</li>
            <li>XYZ Gym is not responsible for personal belongings.</li>
            <li>Membership validity starts from activation date.</li>
            <li>Management reserves the right to modify plans or rules.</li>
        </ul>

        <button class="btn primary" onclick="closeTermsPopup()">I Understand</button>
    </div>
</div>

<body>

<!-- ================= NAVBAR ================= -->
<header>
    <nav class="navbar">
        <div class="nav-left">
            <img src="../images/logo.png" alt="XYZ Gym Logo" class="logo-img">
            <span class="logo-text">XYZ GYM</span>
        </div>

        <ul class="nav-links">
            <li><a href="/home">Home</a></li>
            <li><a href="#plans">Plans</a></li>
            <li><a href="#trainers">Trainers</a></li>
            <li><a href="#contact">Contact</a></li>
            <li>
                <button type="button" class="login-btn" onclick="openLoginModal()">Login</button>
            </li>

        </ul>
    </nav>

</header>

<!-- ================= HERO ================= -->
<section class="hero">
    <div class="hero-overlay"></div>

    <div class="hero-content">
        <h1>Build Your Body<br><span>Transform Your Life</span></h1>
        <p>Join XYZ Gym – Where Fitness Becomes Lifestyle</p>

        <div class="hero-buttons">
            <button type="button" class="btn primary" onclick="openLoginModal()">Join Now</button>
            <a href="#plans" class="btn secondary">View Plans</a>
        </div>

    </div>
</section>

<!-- ================= STATS ================= -->
<section class="stats">
    <div class="stat-card">
        <h2>${membersCount}</h2>
        <p>Members</p>
    </div>
    <div class="stat-card">
        <h2>${trainersCount}</h2>
        <p>Trainers</p>
    </div>
    <div class="stat-card">
        <h2>${plansCount}</h2>
        <p>Plans</p>
    </div>
</section>

<!-- ================= PLANS ================= -->
<section class="plans" id="plans">
    <h2>Membership Plans</h2>
    <p class="section-subtitle">
        Flexible plans designed to fit your fitness goals
    </p>


    <div class="plan-grid">
        <c:forEach var="plan" items="${plansList}">
            <div class="plan-card">
                <h3>${plan.planName}</h3>
                <p>${plan.durationMonths} Months</p>
                <h4>₹ ${plan.price}</h4>
                <a href="javascript:void(0)" 
                class="btn primary small"
                onclick="openLoginModal()">
                Get Started
                </a>

            </div>
        </c:forEach>

        <div class="plan-card highlight">
            <h3>${plan.planName}</h3>
            <p class="duration">${plan.durationMonths} Months</p>

            <ul class="plan-features">
                <li><i class="fa fa-check"></i> Full Gym Access</li>
                <li><i class="fa fa-check"></i> Free Trainer Guidance</li>
                <li><i class="fa fa-check"></i> Diet Consultation</li>
                <li><i class="fa fa-check"></i> Modern Equipment</li>
            </ul>

            <h4 class="price">₹ ${plan.price}</h4>

            <button class="btn primary small" onclick="openLoginModal()">
                Join Now
            </button>
        </div>

    </div>
</section>

<!-- ================= TRAINERS ================= -->
<section class="trainers" id="trainers">
    <h2>Our Trainers</h2>

    <div class="trainer-grid">
        <c:forEach var="trainer" items="${topTrainers}">
            <div class="trainer-card">
                <img src="../images/trainer-placeholder.jpg">
                <h4>${trainer.name}</h4>
                <!-- <p>${trainer.specialization}</p> -->
            </div>
        </c:forEach>
    </div>
</section>

<!-- ================= CONTACT ================= -->
<section class="contact-section" id="contact">
    <h2>Contact Us</h2>
    <p>We’re here to help you start your fitness journey</p>

    <div class="contact-grid">
        <div class="contact-item">
            <i class="fa fa-phone"></i>
            <h4>Phone</h4>
            <p>+91 8055343217</p>
        </div>

        <div class="contact-item">
            <i class="fa fa-envelope"></i>
            <h4>Email</h4>
            <p>karanharer094@gmail.com</p>
        </div>

        <div class="contact-item">
            <i class="fa fa-location-dot"></i>
            <h4>Address</h4>
            <p>Pune, Maharashtra, India</p>
        </div>
    </div>
</section>

<!-- ================= FOOTER ================= -->
<footer>
    <div class="footer-links">
        <a href="javascript:void(0)" onclick="openTermsPopup()">Terms & Conditions</a>
        <a href="javascript:void(0)" onclick="openRefundPopup()">Cancellation & Refund Policy</a>
        <a href="#contact">Contact Us</a>
    </div>


    <div class="footer-bottom">
        © 2025 XYZ Gym | All Rights Reserved
    </div>
</footer>


<script src="../js/home.js"></script>
</body>
</html>
