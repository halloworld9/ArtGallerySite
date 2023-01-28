async function uploadFile() {
    let formData = new FormData();
    formData.append("cover", cover.files[0]);
    formData.append("visible", visible.checked);
    formData.append("title", title.value)
    formData.append("description", description.value)
    let list = photos.files;
    for (let i = 0; i < list.length; i++)
        formData.append("photos", list[i]);


    await fetch('admin', {
        method: "PUT",
        body: formData
    });
    window.location.reload();
}

function imageSize(img) {
    img.style.width = window.innerWidth / 5 + 'px';
    img.style.height = window.innerHeight / 4 + 'px';
}

function figureSize(figure) {
    for (let i in document.getElementsByTagName("figcaption")) {
        i.style.width = window.innerWidth / 5 + 'px';
    }
}