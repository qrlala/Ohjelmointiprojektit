import React from 'react';
import {Slider} from '@material-ui/core';
import {Box} from '@material-ui/core/';

function Sliders(){
  const [Color, setColor] = React.useState('rgb(128, 128, 128)');
  
  const ChangeValue = colour => (event, newValue) => {
    var newColor = Color;
    var numbers;
    var readyColor;
    if (colour === "red"){
      numbers = splitStrings(newColor);
      readyColor = 'rgb(' + newValue + ", " + numbers[1] + ", " + numbers[2] + ')';
      console.log(readyColor);
      setColor(readyColor);
    }
    else if (colour === "green"){
      numbers = splitStrings(newColor);
      readyColor = 'rgb(' + numbers[0] + ", " + numbers[1] + ", " + newValue + ')';
      console.log(readyColor);
      setColor(readyColor);
    }
    if (colour === "blue"){
      numbers = splitStrings(newColor);
      readyColor = 'rgb(' + numbers[0] + ", " + newValue + ", " + numbers[2] + ')';
      console.log(readyColor);
      setColor(readyColor);
    }

  }
  function splitStrings(string){
    var whole = string.split('(');
    var temp = whole[1].replace(')', '');
    var parts = temp.split(', ');
    return parts;
  }

  return (
    <Box padding="20px" style={{height:"200px", background: Color, width:"200px"}}>
        <Slider
          orientation="vertical"
          valueLabelDisplay="auto"
          max={255}
          defaultValue={128}
          onChange={ChangeValue("red")}
        />
        <Slider
          orientation="vertical"
          valueLabelDisplay="auto"
          defaultValue={128}
          max={255}
          onChange={ChangeValue("green")}
        />
        <Slider
          orientation="vertical"
          valueLabelDisplay="auto"
          max={255}
          defaultValue={128}
          onChange={ChangeValue("blue")}
        />
      </Box>
  );
}

export default Sliders;
