// 이미지 base64 변환
function getBase64(file, onloaded) {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = onloaded;
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}