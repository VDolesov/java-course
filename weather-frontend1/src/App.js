import React, { useState, useEffect } from 'react';
import './App.css';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [token, setToken] = useState(localStorage.getItem('jwtToken') || '');
  const [city, setCity] = useState('');
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    localStorage.setItem('jwtToken', token);
  }, [token]);

  const handleAuth = async (endpoint) => {
    if (!username || !password) {
      setError('Введите имя пользователя и пароль.');
      return;
    }
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`http://localhost:8080/auth/${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error(`Ошибка авторизации: ${errorText}`);
        if (errorText.includes('Bad credentials')) {
          throw new Error('Неверный логин или пароль.');
        } else if (errorText.includes('User already exists')) {
          throw new Error('Пользователь уже существует.');
        } else {
          throw new Error(errorText);
        }
      }

      const data = await response.json();
      setToken(data.token);
    } catch (err) {
      console.error(err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleWeatherFetch = async () => {
    if (!city) {
      setError('Введите название города.');
      return;
    }
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`http://localhost:8080/weather/${encodeURIComponent(city)}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!response.ok) throw new Error(await response.text());
      const data = await response.json();
      setWeather(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    setToken('');
    localStorage.removeItem('jwtToken');
    setWeather(null);
  };

  const hourlyData =
    weather?.hourlyTemperatures?.map((temp, index) => ({
      hour: `${index}:00`,
      temperature: temp,
    })) || [];

  return (
    <div className="App">
      <h1 className="text-center">Weather App</h1>

      {!token ? (
        <div className="form">
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button onClick={() => handleAuth('login')}>Login</button>
          <button onClick={() => handleAuth('register')}>Register</button>
        </div>
      ) : (
        <div className="weather-form">
          <div className="logout-row">
            <input
              type="text"
              placeholder="Enter city"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
            <button onClick={handleWeatherFetch}>Получить прогноз</button>
            <button className="logout-btn" onClick={handleLogout}>Выйти</button>
          </div>
        </div>
      )}

      {loading && <p>Загрузка...</p>}
      {error && <p className="error-text">{error}</p>}

      {weather && (
        <div className="weather-data">
          <h2>{weather.city}</h2>
          <p>Температура: {weather.temperature}°C</p>
          <p>Ощущается как: {weather.feelsLike}°C</p>
          <p>Давление: {weather.pressure} Pa</p>
          <p>Ветер: {weather.wind} m/s</p>
          <p>Направление: {weather.windDirection}°</p>
          <p>Облачность: {weather.cloudCover}%</p>
          <p>Видимость: {weather.visibility} m</p>
          <p>Обновлено: {new Date(weather.updatedAt).toLocaleString()}</p>

          <h3>Температурный диапазон</h3>
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={hourlyData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="hour" />
              <YAxis domain={['auto', 'auto']} />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="temperature" stroke="#8884d8" activeDot={{ r: 8 }} />
            </LineChart>
          </ResponsiveContainer>
        </div>
      )}
    </div>
  );
}

export default App;
