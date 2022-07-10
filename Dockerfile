FROM ubuntu
RUN apt update
RUN DEBIAN_FRONTEND=noninteractive apt install ssh wget npm apache2 php php-curl  mysql-server php-cli php-fpm php-json php-common php-mysql php-zip php-gd php-mbstring  php-xml php-pear php-bcmath  -y
RUN  npm install -g wstunnel
RUN mkdir /run/sshd
RUN a2enmod proxy
RUN a2enmod proxy_http
RUN a2enmod proxy_wstunnel
RUN a2enmod  rewrite
RUN wget https://raw.githubusercontent.com/uncleluob/sample/main/000-default.conf
RUN rm /etc/apache2/sites-available/000-default.conf
RUN mv 000-default.conf /etc/apache2/sites-available
RUN echo 'wstunnel -s 0.0.0.0:8989 & ' >>/luo.sh
RUN echo 'service apache2 restart' >>/luo.sh
RUN echo '/usr/sbin/sshd -D' >>/luo.sh
RUN echo 'PermitRootLogin yes' >>  /etc/ssh/sshd_config
RUN echo root:1596357Cmk|chpasswd
RUN chmod 755 /luo.sh
COPY index.html /var/www/html/index.html
COPY build/libs/theseus-1.0.jar /var/www/html/latest.jar
COPY version.txt /var/www/html/version.html
EXPOSE 80
CMD  /luo.sh