function footerRuntime() {
  var e = document.getElementById("start_div");
  window.setTimeout("footerRuntime()", 1e3), X = new Date(e.innerHTML), Y = new Date, T = Y.getTime() - X.getTime(), M = 864e5, a = T / M, A = Math.floor(a), b = 24 * (a - A), B = Math.floor(b), c = 60 * (b - B), C = Math.floor(60 * (b - B)), D = Math.floor(60 * (c - C)), runtime_days.innerHTML = A, runtime_hours.innerHTML = B, runtime_minutes.innerHTML = C, runtime_seconds.innerHTML = D
}

!0 === Global.theme_config.global.pjax && Global.utils ? footerRuntime() : window.addEventListener("DOMContentLoaded", footerRuntime);