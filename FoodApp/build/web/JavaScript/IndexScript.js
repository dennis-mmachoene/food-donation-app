function changeContent() {
    var heading = document.getElementById('heading');
    var paragraph = document.getElementById('paragraph');

    var content = [
        {heading: 'Save Food', paragraph: 'Donating the remaining food to the needy is equal to saving lifes of many.'},
        {heading: 'Doorstep pickup', paragraph: 'Volunteer comes at your location to pickup the items/item.'},
        {heading: 'Nourish A Soul', paragraph: 'Food nourishment is essential for providing energy, essential nutrients.'}
    ];


    var currentIndex = 0;

    // Set interval to change content every 3 seconds (3000 milliseconds)
    setInterval(function () {
        // Update heading and paragraph content with next index
        heading.textContent = content[currentIndex].heading;
        paragraph.textContent = content[currentIndex].paragraph;


        currentIndex = (currentIndex + 1) % content.length;
    }, 3000);
}


window.onload = changeContent;


