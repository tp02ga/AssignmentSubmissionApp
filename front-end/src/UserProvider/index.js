import React, { createContext, useContext, useState } from "react";
import Cookies from "js-cookie";
const UserContext = createContext();

const UserProvider = ({ children }) => {
  console.log("All Cookies", Cookies.get());
  const [jwt, setJwt] = useState(Cookies.get("jwt"));
  console.log("jwt value in UserProvider is: ", jwt);

  const value = { jwt, setJwt };
  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

function useUser() {
  const context = useContext(UserContext);
  if (context === undefined) {
    throw new Error("useUser must be used within a UserProvider");
  }

  return context;
}

export { useUser, UserProvider };
