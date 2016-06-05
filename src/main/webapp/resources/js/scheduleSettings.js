function settings(schi) {
    while (!schi.classList.contains("schi")) {
        schi = schi.parentNode
        if (schi == document) return
    }
    
    settings.show()
    
}