FROM node AS builder
WORKDIR /app
ARG BACKEND_URL
ENV BACKEND_URL=$BACKEND_URL
COPY package.json ./
RUN npm install
COPY . ./
RUN npm run build

FROM nginx
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/build .
ENTRYPOINT ["nginx", "-g", "daemon off;"]
