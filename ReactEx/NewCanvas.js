import * as React from 'react';
import {Button} from '@material-ui/core';
import {Box} from '@material-ui/core/';

function NewCanvas(){

    const someRef = React.useRef(null);

    const onClick =() => {
        let shape = someRef.current.getContext("2d");
        shape.fillStyle="red";
        shape.fillRect(60,170,100,10);
        
        shape.translate(80, 120);
        shape.rotate(60);
        shape.translate(-80, -120);

        shape.fillStyle="red";
        shape.fillRect(30,100,45,10);
    }

    return(
        <Box padding="30px" height="100px" width="200px" sx={{backgroundColor:'primary.main'}} >
            <header classname="App-header">
                Canvas
            <canvas
               width={500}
               height={350}
               ref={someRef}>
            </canvas>
            <div>
                <Button variant="outlined" color="secondary" onClick={onClick}>Draw</Button>
            </div>
            </header>
        </Box>
    );
}
export default NewCanvas;