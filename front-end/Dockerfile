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

# production environment
FROM node:16-alpine as prod
COPY --from=build /react-ui/build /usr/share/nginx/html
EXPOSE 3000