import React, { useState } from 'react';
import axios from 'axios';

function WeatherForm({ onDataReceived }) {
  const [city, setCity] = useState('');
  const [token, setToken] = useState('');

  const fetchWeather = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/weather/${city}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      onDataReceived(response.data);
    } catch (error) {
      alert('Ошибка запроса: ' + error.message);
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Город"
        value={city}
        onChange={e => setCity(e.target.value)}
      />
      <input
        type="text"
        placeholder="JWT Token"
        value={token}
        onChange={e => setToken(e.target.value)}
      />
      <button onClick={fetchWeather}>Получить прогноз</button>
    </div>
  );
}

export default WeatherForm;
