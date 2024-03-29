document.addEventListener("DOMContentLoaded", function () {
    const canvas = document.getElementById("oceanCanvas");
    const ctx = canvas.getContext("2d");

    // Load background image
    const backgroundImage = new Image();
    backgroundImage.src = 'ocean.png'; // Replace with the path to your background image

    // Creature constructor
    class Creature {
        constructor(x, y, type) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.speedX = Math.random() * 0.1  + 1; // Random speed
            this.speedY = (type === "crab") ? 0 : Math.random() * 0.5 - 1; // Crabs only move horizontally
            this.image = new Image();
            this.image.src = this.getImagePath(type); // Set image based on creature type
            this.flipped = false; // Track if the image is flipped
        }

        getImagePath(type) {
            switch (type) {
                case "fish":
                    return 'fish1.png';
                case "crab":
                    return 'crab1.png';
                case "turtle":
                    return 'turtle1.png';
            }
        }

        draw() {
            ctx.save();
            ctx.translate(this.x, this.y);
            if (this.flipped) {
                ctx.scale(-1, 1);
            }
            ctx.drawImage(this.image, -30, -15, 60, 30); // Double the size
            ctx.restore();
        }

        update() {
            this.x += this.speedX;
            if (this.type !== "crab") { // Allow non-crab creatures to move vertically
                this.y += this.speedY;
            }

            // Change direction and flip image when hitting canvas borders
            if (this.x < 0 || this.x > canvas.width) {
                this.speedX *= -1;
                this.flipped = !this.flipped; // Flip the image
            }
            if (this.y < 0 || this.y > canvas.height) {
                this.speedY *= -1;
            }

            // Keep crabs on the bottom
            if (this.type === "crab") {
                this.y = canvas.height - 30;
            }
        }
    }

    const creatures = []; // Initialize empty creatures array

    // Function to create a random creature based on rarity
    function createRandomCreature(x, y) {
      const rarity = Math.random();
      let type;

      if (rarity < 0.4) {
          type = "fish";
      } else if (rarity < 0.7) { // Adjusted for crabs
          type = "crab";
      } else {
          type = "turtle"; // This covers the remaining probability
      }

      creatures.push(new Creature(x, y, type));
    }


    // Add a new creature on canvas click
    canvas.addEventListener("click", function (event) {
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        createRandomCreature(x, y);
    });

    function animate() {
        ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);

        for (const creature of creatures) {
            creature.update();
            creature.draw();
        }

        requestAnimationFrame(animate); // Request the next frame
    }

    animate();


});
