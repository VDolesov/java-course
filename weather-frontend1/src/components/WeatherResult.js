import React from 'react';

function WeatherResult({ data }) {
  return (
    <div className="result-box">
      <h2>Погода в городе: {data.city}</h2>
      <p><strong>Температура:</strong> {data.temperature} °C</p>
      <p><strong>Ощущается как:</strong> {data.feelsLike} °C</p>
      <p><strong>Точка росы:</strong> {data.dewPoint} °C</p>
      <p><strong>Давление:</strong> {data.pressure} Па</p>
      <p><strong>Ветер:</strong> {data.wind} м/с</p>
      <p><strong>Направление ветра:</strong> {data.windDirection}°</p>
      <p><strong>Облачность:</strong> {data.cloudCover} %</p>
      <p><strong>Видимость:</strong> {data.visibility} м</p>
      <p><strong>Координаты:</strong> {data.latitude}, {data.longitude}</p>
      <p><strong>Обновлено:</strong> {new Date(data.updatedAt).toLocaleString()}</p>
      <p><strong>Последний доступ:</strong> {new Date(data.lastAccessedAt).toLocaleString()}</p>
      <p><strong>Кол-во запросов:</strong> {data.requestCount}</p>
    </div>
  );
}

export default WeatherResult;
