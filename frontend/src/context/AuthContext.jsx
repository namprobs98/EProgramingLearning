import { createContext, useContext, useMemo, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      setUser(null);
      localStorage.removeItem('user');
    }
  }, []);

  const login = (authData) => {
    const userData = {
      userId: authData.userId,
      name: authData.name,
      email: authData.email,
      role: authData.role,
    };
    localStorage.setItem('token', authData.token);
    localStorage.setItem('user', JSON.stringify(userData));
    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  const value = useMemo(
    () => ({ user, login, logout, isAuthenticated: !!user }),
    [user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}
