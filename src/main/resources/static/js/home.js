/* =========================
   NAVBAR SCROLL EFFECT
========================= */
window.addEventListener("scroll", () => {
    const header = document.querySelector("header");
    if (!header) return;

    if (window.scrollY > 50) {
        header.style.background = "#020617";
        header.style.boxShadow = "0 4px 12px rgba(0,0,0,0.4)";
    } else {
        header.style.background = "#0f172a";
        header.style.boxShadow = "none";
    }
});

/* =========================
   COUNTER ANIMATION
========================= */
const counters = document.querySelectorAll(".stat-card h2");
let counterStarted = false;

function startCounter() {
    counters.forEach(counter => {
        const target = +counter.innerText;
        let count = 0;
        const increment = target / 60;

        const update = () => {
            count += increment;
            if (count < target) {
                counter.innerText = Math.ceil(count);
                requestAnimationFrame(update);
            } else {
                counter.innerText = target;
            }
        };
        update();
    });
}

window.addEventListener("scroll", () => {
    const stats = document.querySelector(".stats");
    if (!stats) return;

    if (stats.getBoundingClientRect().top < window.innerHeight && !counterStarted) {
        startCounter();
        counterStarted = true;
    }
});

/* =========================
   MODAL HELPERS
========================= */
function openModal(id) {
    const modal = document.getElementById(id);
    if (!modal) return;

    modal.classList.add("show");
    document.body.style.overflow = "hidden"; // background scroll बंद
}

function closeModal(id) {
    const modal = document.getElementById(id);
    if (!modal) return;

    modal.classList.remove("show");
    document.body.style.overflow = ""; // scroll परत चालू
}

/* =========================
   LOGIN / REGISTER
========================= */
function openLoginModal() {
    openModal("loginModal");
}

function closeLoginModal() {
    closeModal("loginModal");
}

function openRegisterPopup() {
    closeLoginModal();
    openModal("registerPopup");
}

function closeRegisterPopup() {
    closeModal("registerPopup");
}

/* =========================
   TERMS / REFUND POPUP
========================= */
function openTermsPopup() {
    openModal("termsPopup");
    history.pushState({}, "", "/terms");
}

function closeTermsPopup() {
    closeModal("termsPopup");
    history.pushState({}, "", "/home");
}

function openRefundPopup() {
    openModal("refundPopup");
    history.pushState({}, "", "/refund");
}

function closeRefundPopup() {
    closeModal("refundPopup");
    history.pushState({}, "", "/home");
}

/* =========================
   AUTO OPEN BASED ON URL
========================= */
window.addEventListener("load", () => {
    const path = window.location.pathname;

    if (path === "/terms") openTermsPopup();
    if (path === "/refund") openRefundPopup();
});

/* =========================
   CLOSE MODAL ON OVERLAY CLICK
========================= */
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("modal")) {
        e.target.classList.remove("show");
        document.body.style.overflow = "";
        history.pushState({}, "", "/home");
    }
});
