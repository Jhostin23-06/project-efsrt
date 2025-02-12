document.addEventListener('DOMContentLoaded', function () {
    const textElement = document.getElementById('typewriter-text');
    const firstLine = textElement.childNodes[0].textContent.trim();
    const secondLine = textElement.querySelector('.new-line').textContent.trim();
    textElement.childNodes[0].textContent = '';
    textElement.querySelector('.new-line').textContent = '';

    let index = 0;
    function typeFirstLine() {
        if (index < firstLine.length) {
            textElement.childNodes[0].textContent += firstLine.charAt(index);
            index++;
            setTimeout(typeFirstLine, 100); // Adjust typing speed here
        } else {
            index = 0;
            setTimeout(typeSecondLine, 100); // Start typing the second line
        }
    }

    function typeSecondLine() {
        if (index < secondLine.length) {
            textElement.querySelector('.new-line').textContent += secondLine.charAt(index);
            index++;
            setTimeout(typeSecondLine, 100); // Adjust typing speed here
        }
    }

    typeFirstLine();
});