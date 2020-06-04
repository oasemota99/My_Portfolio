// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ["I've travelled to 10 different countries", "My favorite food is spaghetti", "I'm Nigerian", "I am the best at Mario Kart","I used to play rugby."];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
async function getQuote(){
    const response = await fetch('/data');
    const quote = await response.text();
    document.getElementById('quote-container').innerText = quote;
}
async function getMessage(){
    const response = await fetch('/data');
    const msg = await response.text();
    document.getElementById('sentient').innerText = msg;
}

