


            //============================================================
            //가격표 컨버터
            //============================================================
            const priceConverter = (number) => {



                return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

            };

