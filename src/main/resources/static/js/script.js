document.addEventListener('DOMContentLoaded', function () {


});

function load_list() {
    document.querySelector('.circle').style.animationPlayState = 'running';
    document.querySelector('.right').style.animationPlayState = 'running';
    document.querySelector('.circle').addEventListener('animationend', () => {
        document.querySelector('.circle').remove();
        document.querySelector('.right').remove();
        document.querySelector('.main-interface').style.animationPlayState = 'running';
    });
}

function submitForm() {
    document.querySelector('.main-interface').style.display = 'none';
    document.querySelector('.loading-screen').style.display = 'block';
    sleep(2000).then(() => {
        document.querySelector('#real-submit').click();
    });
}

function sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}