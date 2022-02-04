# stage1 as build
FROM node:16-alpine as build

# copy the package.json to install dependencies
COPY package.json package-lock.json ./

# Install the dependencies and make the folder
RUN npm install && mkdir /react-ui && mv ./node_modules ./react-ui

WORKDIR /react-ui

COPY . .

RUN npm config set proxy http://back-end:8080

# Build the project and copy the files
RUN npm run build


#OLD-------
# build environment
# FROM node:14.15.0 as build

# # set working directory
# WORKDIR /app

# # add `/app/node_modules/.bin` to $PATH
# ENV PATH /app/node_modules/.bin:$PATH

# # install app dependencies
# COPY package.json ./
# COPY package-lock.json ./
# RUN npm install
# RUN npm install react-scripts@3.4.1 -g --silent

# # add app
# COPY . ./

# RUN npm config set proxy http://app-server:8080

# RUN npm run build

# start app
#CMD ["npm", "start"]

# production environment
FROM nginx:1.16.0-alpine
RUN rm /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html/*
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=build /react-ui/build /usr/share/nginx/html
EXPOSE 3000 80 443
CMD ["nginx", "-g", "daemon off;"]