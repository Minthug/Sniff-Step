import { NextResponse } from 'next/server'

const fields = [
    { name: 'title', message: 'title should not be empty' },
    { name: 'images', message: 'images should not be empty' },
    { name: 'description', message: 'description should not be empty' },
    { name: 'activityLocation', message: 'address should not be empty' },
    { name: 'activityDate', message: 'activityDate should not be empty' },
    { name: 'activityTime', message: 'activityTime should not be empty' }
]

export async function POST(req: Request) {
    const formdata = await req.formData()
    const messages = []

    for (const field of fields) {
        const value = formdata.get(field.name)
        if (!value) {
            messages.push(field.message)
        }
    }

    if (messages.length) {
        return NextResponse.json({ message: messages }, { status: 400 })
    }

    const res = await fetch(process.env.JAVA_BACKEND_URL + '/v1/boards', {
        method: 'POST',
        headers: {
            authorization: req.headers.get('authorization') || ''
        },
        body: formdata
    })

    if (!res.ok) {
        const { message, error, status } = await res.json()
        return NextResponse.json({ message, error }, { status })
    }

    return NextResponse.json({ message: 'success' }, { status: 200 })
}
