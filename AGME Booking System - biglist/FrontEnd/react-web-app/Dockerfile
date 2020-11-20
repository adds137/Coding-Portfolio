FROM node:10-alpine as build-step
RUN mkdir /app
WORKDIR /app
COPY package.json /app
RUN npm install
COPY . /app
RUN npm run build

FROM nginx:1.17.1-alpine
COPY --from=build-step /app/build /usr/share/nginx/html

#Build with: docker build -t com/scrumoftheearth/code/frontend .

#Run Container with: docker run -d -it -p 80:80/tcp --name react-app com/scrumoftheearth/code/frontend:latest
