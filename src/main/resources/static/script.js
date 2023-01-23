async function uploadFile() {
    let formData = new FormData();
    formData.append("image", image.files[0]);
    formData.append("zip", zip.files[0]);


    await fetch('admin?' + new URLSearchParams({
        title: document.getElementById("title").value,
        description: document.getElementById("description").value
    }), {
        method: "POST",
        body: formData
    });
    window.location.reload();
}
