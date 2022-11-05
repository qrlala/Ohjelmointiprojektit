/* Movie search
 * Laura Launonen (laura.launonen@tuni.fi)
 * Ville Sarin (ville-vesa.sarin@tuni.fi)
 * 
 * In this project, the pictures of movies are switched on and off with togglebuttons.
 * Using CSS they toggle between two classes, hidden & visible.
 * Slider is not working, but is in code for aesthetic reasons. 
 * We use hooks to set the movies visible/hidden.
 * Entirety of code was written together, discussing on discord at the same time.
 * 
 */
import * as React from 'react';
import {Box, Button, Slider} from '@material-ui/core';
import { ToggleButton, ToggleButtonGroup } from '@mui/material';
import { useState } from "react";
import './Movie.css';

function Movie() {

  {/* Hooks, that set movies visible/hidden */}
  const [isAction, setAction] = React.useState("false");
  const [isDrama, setDrama] = React.useState("false");
  const [isHorror, setHorror] = React.useState("false");
  const [isComedy, setComedy] = React.useState("false");
  const [isRomance, setRomance] = React.useState("false");

  const handleChange = () => {
    setAction(!isAction);
  };
  const handleChange2 = () => {
    setDrama(!isDrama);
  };
  const handleChange3 = () => {
    setHorror(!isHorror);
  };
  const handleChange4 = () => {
    setComedy(!isComedy);
  };
  const handleChange5 = () => {
    setRomance(!isRomance);
  };
  
  
  // Slider implementation.
  const [value, setValue] = React.useState([1900, 2022]);
  // This function is not in use, it was for the slider.
  const handleChange1 = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className='screen'>
      <div>
        {/* This box keeps buttons and slider in proper position. */}
          <Box
            padding="30px"
            height="270px"
            width="200px"
          >
          <ToggleButtonGroup
            orientation="vertical"
            >
            <h3>Genre</h3>
                <ToggleButton value="comedy" 
                  onClick={handleChange4}
                 > 
                  Comedy
                </ToggleButton>
                <ToggleButton value="drama"
                onClick={handleChange2}> 
                Drama
                </ToggleButton>
                <ToggleButton value="horror"
                onClick={handleChange3}> 
                Horror
                </ToggleButton>
                <ToggleButton value="Action"
                onClick={handleChange}> 
                Action
                </ToggleButton>
                <ToggleButton value="romance"
                onClick={handleChange5}> 
                Romance
                </ToggleButton>
          </ToggleButtonGroup>
          </Box>
          <Box padding="20px"
              height="300px"
              width="200px">
                {/* INACTIVE, Slider to limit movies by their publishing year. */}
              <Slider
                  value={value}
                  onChange={handleChange1}
                  valueLabelDisplay="on"
                  min={1900}
                  max={2022} />
          </Box>
      </div>
      {/* Here are all the thumbnails of the movies, which the program fetches from the internet. */}
      <div className="Window"> 
          <img className={isComedy ? "comedy" : "hidden"} 
          src="https://upload.wikimedia.org/wikipedia/fi/3/3a/Spider-Man_No_Way_Home.png"/>
          <img className={isRomance ? "romantic" : "hidden"}
          src="https://m.media-amazon.com/images/M/MV5BMTk3OTM5Njg5M15BMl5BanBnXkFtZTYwMzA0ODI3._V1_.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
          src="https://dx35vtwkllhj9.cloudfront.net/paramountpictures/sonic-the-hedgehog-2/images/regions/us/onesheet.jpg" />
          <img className={isAction ? "action" : "hidden"}
          src="https://musicart.xboxlive.com/7/a60e5200-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080" />
          <img className={isComedy ? "comedy" : "hidden"} 
          src="https://i-viaplay-com.akamaized.net/viaplay-prod/554/172/1636023220-11c4858634f6c1143b5c1576d5035d358965b73c.jpg?width=400&height=600" />
          <img className={isDrama ? "drama" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/0/01/Interstellar.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
            src="https://i-viaplay-com.akamaized.net/viaplay-prod/873/244/1474891810-e4e54cb45332b8f0503853593503251e10d2cc35.jpg?width=400&height=600" />
          <img className={isHorror ? "horror" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/b/b9/Scream.jpg"/>
          <img className={isDrama ? "drama" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/9/96/Mononoke.jpg" />
          <img className={isDrama ? "drama" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/7/78/Naapurinitotoro.png" />
          <img className={isDrama ? "drama" : "hidden"}
            src="https://m.media-amazon.com/images/M/MV5BMzA5Zjc3ZTMtMmU5YS00YTMwLWI4MWUtYTU0YTVmNjVmODZhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg" />
          <img className={isDrama ? "drama" : "hidden"}
            src="https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg" />
          <img className={isHorror ? "horror" : "hidden"} 
          src="https://upload.wikimedia.org/wikipedia/fi/3/3b/Human-Centipede-poster.jpg" />
          <img className={isRomance ? "romantic" : "hidden"}
          src="https://www.themoviedb.org/t/p/original/e1Hi1ym2hJFN5V2xNjTNiD7OS5s.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
          src="https://m.media-amazon.com/images/M/MV5BZDQyODUwM2MtNzA0YS00ZjdmLTgzMjItZWRjN2YyYWE5ZTNjXkEyXkFqcGdeQXVyMTI2MzY1MjM1._V1_.jpghttps://m.media-amazon.com/images/M/MV5BZDQyODUwM2MtNzA0YS00ZjdmLTgzMjItZWRjN2YyYWE5ZTNjXkEyXkFqcGdeQXVyMTI2MzY1MjM1._V1_.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/thumb/e/ef/POTC3.jpg/800px-POTC3.jpg" />
          <img className={isHorror ? "horror" : "hidden"}
          src="https://upload.wikimedia.org/wikipedia/fi/d/d8/ItMovie2017.jpg" />
          <img className={isRomance ? "romantic" : "hidden"}
          src="https://upload.wikimedia.org/wikipedia/fi/2/22/Titanic_poster.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
            src="https://images-na.ssl-images-amazon.com/images/S/pv-target-images/655e1f2eb280a82d4df1dcd01e4b59c0368aa06460d8194ff18e91e40c5071a4._RI_V_TTW_.jpg" />
          <img className={isComedy ? "comedy" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/b/b9/Intouchables.jpg" />
          <img className={isAction ? "action" : "hidden"}
            src="https://upload.wikimedia.org/wikipedia/fi/7/7f/Morbius_Movie.png" />
          <img className={isAction ? "action" : "hidden"}
            src="https://images.justwatch.com/poster/263785757/s718/tahtien-sota.%7Bformat%7D" />
      </div>
  </div>
  );
};

export default Movie;