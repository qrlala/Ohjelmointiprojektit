import React from 'react';
import {Button} from '@material-ui/core';
import {TextField} from '@material-ui/core';
import {Box} from '@material-ui/core/';
import {Checkbox} from '@material-ui/core';

function Binary(){
  
  const [binaries, setBinary] = React.useState("00000000");
  const [decimal, setDecimal] = React.useState(0);

    const update = inx => (event) => {       
        if (event.target.checked === true){
            var bnr = binaries
            bnr = setCharAt(bnr,inx,'1');
            setBinary(bnr);
        }
        else {
            var bnr = binaries
            bnr = setCharAt(bnr,inx,'0');
            setBinary(bnr);
        }
    }

    function setCharAt(str,index,chr) {
        if (index > str.length-1) return str;
        return str.substring(0,index) + chr + str.substring(index+1);
    }

    const convert = (event) => {
        const des = parseInt(binaries, 2)
        setDecimal(des);
    }

  return (
    <Box padding="10px" >
        <div>
            <Checkbox
                color="default"
                onChange={update(0)}
            />
            <Checkbox
                color="default"
                onChange={update(1)}
            />
            <Checkbox
                value="2"
                color="default"
                onChange={update(2)}
            />
            <Checkbox
                value="3"
                color="default"
                onChange={update(3)}
            />
            <Checkbox
                value="4"
                color="default"
                onChange={update(4)}
            />
            <Checkbox
                value="5"
                color="default"
                onChange={update(5)}
            />
            <Checkbox
                value="6"
                color="default"
                onChange={update(6)}
            />
            <Checkbox
                value="7"
                color="default"
                onChange={update(7)}
            />
        </div>
        <Box padding="10px">
            <TextField
              value={binaries}
              id="binaries"
              size="small"
              variant="outlined"
            />
        </Box>
        <div>
          <Button variant="contained" color="primary" onClick={convert}>
            Convert
          </Button>
        </div>
        <Box padding="10px">
            <TextField
                id="decimal"
                value={decimal}
                size="small"
                variant="outlined"
            />
        </Box>
    </Box>
  );
}

export default Binary;
