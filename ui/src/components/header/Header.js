import React from "react";
import Navbar from 'react-bootstrap/Navbar'

const Header = () => {

    return (
      <>
          <Navbar bg="dark" variant="dark">
              <Navbar.Brand>
                  Currency converter
              </Navbar.Brand>
          </Navbar>

      </>
    );

};

export default Header