const data = {
    products: [
        {
            id: '1',
            name: 'Laptop',
            description: 'Acer NoteBook is a Windows 10 laptop with a 15.60-inch display. It is powered by a Core i3 processor and it comes with 12GB of RAM. The Acer NoteBook packs 128GB of SSD storage. Graphics are powered by Intel HD Graphics 620.',
            image: 'https://static.acer.com/up/Resource/Acer/Laptops/Aspire_Vero/Images/20210927/Aspire-Vero-AV15-51_modelmain.png',
        },
        {
            id: 2,
            name: 'Hard-Drive',
            description: 'Hard disks are flat circular plates made of aluminum or glass and coated with a magnetic material. Hard disks for personal computers can store terabytes (trillions of bytes) of information. ',
            image: 'https://guide-images.cdn.ifixit.com/igi/DCZoPH4tvNY5NUaV.large'
        },

        {

            id: 3,
            name: 'USB Flash Drive',
            description: 'A USB flash drive is a device used for data storage that includes a flash memory and an integrated Universal Serial Bus(USB) interface.',
            image: 'https://www.cleverfiles.com/howto/wp-content/uploads/2018/04/KAUSB32V30BS-32gb-usb-flash-drive-folded-webres.jpg'
        },
        
        {
                
            id: 4,
            name: 'Computer Mouse',
            description: 'A computer mouse is a hand-held pointing device that detects two-dimensional motion relative to a surface. This motion is typically translated into the motion of a pointer on a display, which allows a smooth control of the graphical user interface of a computer.',
            image: 'https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/input_devices/dell/mouse/wm126/dell-mouse-wm126-504x350.jpg?fmt=jpg'
        

        },
        {
            id: 5,
            name: 'Headset',
            description: 'A headset makes spoken communication possible without having to wear an earpiece or hold a microphone. It replaces, for example, a telephone handset and can be used to talk and listen at the same time. Other common uses of headsets are for gaming or video communications, in conjunction with a computer.',
            image: 'https://resource.logitechg.com/w_1000,c_limit,q_auto,f_auto,dpr_auto/d_transparent.gif/content/dam/gaming/en/products/pro-x/pro-headset-hero.png?v=1 '
        },

        {
            id: 6,
            name: 'Smart Phone',
            description: "A smartphone is a handheld electronic device that provides a connection to a cellular network and the Internet.",
            image: 'https://media-storage.oppostore.co.uk/catalog/product/cache/79a10608e11b4ea1c5a552161a53ae0c/a/1/a15_blue_main1.jpg'
        },

        {
            id: 7,
            name: 'Printer',
            description: " a device that accepts text and graphic output from a computer and transfers the information to paper, usually to standard-size, 8.5 sheets of paper",
            image: 'https://www.collinsdictionary.com/images/full/printer_541385401_1000.jpg'
        }
  ],
};
export default data;

/*const products = [
    { id: 1, name: ' laptop', , type: '2', image: '' },
    { id: 3, name: 'usb', description: 'USB is used to attach keyboards, mice, printers, external storage and mobile devices to the computer. It is also used for charging portable products', type: '1', image: 'data:image/jpeg;base64,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' },
    { id: 4, name: 'mouse', description: 'A computer mouse (plural mice, sometimes mouses) is a hand-held pointing device that detects two-dimensional motion relative to a surface. This motion is typically translated into the motion of a pointer on a display, which allows a smooth control of the graphical user interface of a computer.', type: '2', image: 'https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/input_devices/dell/mouse/wm126/dell-mouse-wm126-504x350.jpg?fmt=jpg' },
    { id: 5, name: 'headset', description: 'A headset makes spoken communication possible without having to wear an earpiece or hold a microphone. It replaces, for example, a telephone handset and can be used to talk and listen at the same time. Other common uses of headsets are for gaming or video communications, in conjunction with a computer.', type: '1', image: 'https://resource.logitechg.com/w_1000,c_limit,q_auto,f_auto,dpr_auto/d_transparent.gif/content/dam/gaming/en/products/pro-x/pro-headset-hero.png?v=1 ' },
    { id: 6, name: 'smart phone', description: "65W Acer Laptop Power Adapter Charger for Acer Aspire Notebook 19V 3.42A Power Cord Connector Size: 5.5 x 1.7mm", type: '1', image: 'https://m.media-amazon.com/images/I/61YHaQV3G6L._AC_SY450_.jpg' },
    { id: 7, name: 'printer', description: " a device that accepts text and graphic output from a computer and transfers the information to paper, usually to standard-size, 8.5 sheets of paper", type: '1', image: 'https://www.collinsdictionary.com/images/full/printer_541385401_1000.jpg' },

]
*/