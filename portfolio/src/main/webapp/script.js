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
    const response = await fetch('/quoteServ');
    const quote = await response.text();
    document.getElementById('quote-container').innerText = quote;
}

async function getMessage(){
    const response = await fetch('/messageServ');
    const msg = await response.json();
    document.getElementById('randommessage').innerText = msg;
}

function createMap() {

  var memLatLng = {lat: 35.1495, lng: -90.0490};
  var gusLatLng = {lat: 35.139368 , lng: -90.057269};
  var indiaPLatLng = {lat: 35.143319, lng: -90.003102};
  var centralBLatLng = {lat: 35.126358, lng: -98.985764};
  var oaoBLatLng = {lat: 35.116002, lng: -89.910065};
  var ttLatLng = {lat: 35.131409, lng: -89.945915};
  var etlLatLng = {lat: 35.136951, lng: -89.974301};

  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: memLatLng, zoom: 11});

  const gusMarker = new google.maps.Marker({
      position: gusLatLng,
      map: map,
      title: "Gus's World Famous Chicken"
  });
  const indiaPMarker = new google.maps.Marker({
      position: indiaPLatLng,
      map: map,
      title: "India Palace"
  });
  const centralBMarker = new google.maps.Marker({
      position: centralBLatLng,
      map: map,
      title: "Central BBQ"
  });
  const oaoBMarker = new google.maps.Marker({
      position: oaoBLatLng,
      map: map,
      title: "One and Only BBQ"
  });
  const ttMarker = new google.maps.Marker({
      position: ttLatLng,
      map: map,
      title: "Tenn Tea: Boba Tea Shop"
  });
  const etlMarker = new google.maps.Marker({
      position: etlLatLng,
      map: map,
      title: "El Toro Loco: Mexican Resturant"
  });
  
}

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(createChart);
function createChart(){
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Resturants');
    data.addColumn('number', 'Count');
    data.addRows([
        ['BBQ', 4],
        ['Mexican', 2],
        ['Chinese', 4],
        ['American',6]
    ]);
    const chartOptions = {
        'title': 'Types of Resturants',
        'width': 600,
        'height': 500
    };
    const chart = new google.visualization.PieChart(
        document.getElementById('chart')
    );
    chart.draw(data, chartOptions);
    
}